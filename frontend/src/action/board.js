import Send from '../components/common/Send';
import { boardActions } from '../slice/board';

export const loadPosts = () => {
    return function (dispatch) {
        /*
        axios
            .get(`${process.env.REACT_APP_API}/board`)
            .then((resp) => {
                dispatch(boardActions.getBoards(resp.data));
            })
            .catch(error => console.log(error));*/

        Send({
            url: '/board/',
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.getBoards(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const addPost = (post) => {
    return function (dispatch) {
        Send({
            url: '/board/write',
            method: 'post',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.addBoard());
            dispatch(loadPosts());
        })
        .catch((error) => console.log(error));
    }
}

export const getSinglePost = (id) => {
    return function (dispatch) {
        Send({
            url: `/board/detail/${id}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.getPost(resp.data.data));
        })
        .catch(error => console.log(error));
        
    }
}

export const updatePost = (post, id) => {
    return function (dispatch) {
        Send({
            url: '/board/update/',
            method: 'put',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.editPost());
        })
        .catch(error => console.log(error));
    }
}

export const deletePost = (id) => {
    return function (dispatch) {
        Send({
            url: `/board/delete/${id}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.deletePost());
            dispatch(loadPosts());
        })
        .catch(error => console.log(error));
    }
}

export const unlikes = (id) => {
    return function (dispatch) {
        Send({
            url: `/board/${id}/unlikes`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.unlikes());
        })
        .catch(error => console.log(error));
    }
}

export const addComment = (id, content) => {
    return function (dispatch) {
        Send({
            url: `/board/${id}/comment`,
            method: 'post',
            data: content,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.addComment());
            dispatch(getSinglePost(id));
        })
        .catch(error => console.log(error));
    }
}