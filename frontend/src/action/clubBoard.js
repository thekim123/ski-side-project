import Send from '../components/common/Send';
import { clubBoardActions } from '../slice/clubBoard';

export const loadPosts = (clubId) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/${clubId}`,
            method: 'get',
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
            dispatch(loadPosts(post.clubId));
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

export const deleteCbPost = (id, clubId) => {
    return function (dispatch) {
        Send({
            url: `/clubBoard/delete/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(clubBoardActions.deletePost());
            dispatch(loadPosts(clubId));
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