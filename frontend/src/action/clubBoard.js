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