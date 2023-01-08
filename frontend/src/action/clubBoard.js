import { createAsyncThunk } from '@reduxjs/toolkit';
import Send from '../components/common/Send';
import { clubBoardActions } from '../slice/clubBoard';

export const loadClubPosts = (clubId) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/${clubId}`,
            method: 'get',
            params: {
                page: 0,
                size: 1000
            },
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.getBoards(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const addPost = (post) => {
    return function (dispatch) {
        Send({
            url: '/clubBoard',
            method: 'post',
            data: post, 
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.addBoard());
            dispatch(loadClubPosts(post.clubId));
        })
        .catch((error) => console.log(error));
    }
}

export const getPost = (id) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/detail/${id}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.getPost(resp.data.data.content[0]));
        })
        .catch(error => console.log(error));
    }
}

export const editPost = (id, post) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/update/${id}`,
            method: 'put',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.editPost());
        })
        .catch(error => console.log(error));        
    }
}

export const asyncEditPost = createAsyncThunk(
    'clubBoardSlice/asyncEditPost',
    async (post) => {
        const resp = await Send({
            url:`/clubBoard/update/${post.id}`,
            method: 'put',
            data: post
        })
        console.log("resp", resp);
        return resp.data;
    }
)

export const deleteCbPost = (id, clubId) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/delete/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.deletePost());
            dispatch(loadClubPosts(clubId));
        })
        .catch(error => console.log(error));        
    }    
}

export const addComment = (comment) => {
    return function (dispatch) {
        Send({
            url: '/clubBoard/reply',
            method: 'post',
            data: comment, 
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.addComment(resp.data.data));
            dispatch(getPost(comment.clubBoardId));
        })
        .catch((error) => console.log(error));
    }    
}

export const deleteClubComment = (commentId, clubId) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/deleteReply/${commentId}`,
            method: 'delete',
        }).then(resp => {
            console.log("delete comment", resp);
            dispatch(clubBoardActions.deleteComment());
            dispatch(getPost(clubId))
        }).catch(error => console.log(error));
    }
}