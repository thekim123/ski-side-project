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
            url: `/board/${id}`,
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.getPost(resp.data));
        })
        .catch(error => console.log(error));
        
    }
}

export const updatePost = (post, id) => {
    return function (dispatch) {
        /*
        axios
            .put(`${process.env.REACT_APP_API}/board/${id}`, post)
            .then((resp) => {
                console.log("resp", resp);
                dispatch(boardActions.editPost());
            })
            .catch(error => console.log(error));*/

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