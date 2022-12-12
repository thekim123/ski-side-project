import Send from '../components/common/Send';
import { boardActions } from '../slice/board';



export const loadPosts = (page) => {
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
            params: {
                page: 0, //몇 번째 페이지인지
                size: 1000 //한번에 몇 개 보일지
            },
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.getBoards(resp.data.data.content));
        })
        .catch(error => console.log(error));
    }
}

export const loadPostsByPage = (currentPage) => {
    return function (dispatch) {
        Send({
            url:`/board/`,
            method: 'get',
            params: {
                page: 2,
                size: 5
            },
            headers: {
                Pageable: {
                    page: 1,
                    size: 5,
                }
            }
        }).then(resp => {
            console.log("resp", resp);
            //dispatch(boardActions.getBoards(resp.data.data.content));
        })
    }
}

export const getByResort = (resortName) => {
    return function (dispatch) {
        Send({
            url: `/board/resort/${resortName}`,
            method: 'get',
            params: {
                page: 0, //몇 번째 페이지인지
                size: 1000 //한번에 몇 개 보일지
            },
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

export const updatePost = (post) => {
    return function (dispatch) {
        Send({
            url: '/board/update/',
            method: 'put',
            data: post,
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.editPost());
            dispatch(loadPosts());
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

export const likes = (id) => {
    return function (dispatch) {
        Send({
            url: `/board/${id}/likes`,
            method: 'post',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.likes());
            dispatch(getSinglePost(id));
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
            dispatch(getSinglePost(id));
        })
        .catch(error => console.log(error));
    }
}

export const addComment = (id, content) => {
    return function (dispatch) {
        Send({
            url: `/board/comment/write`,
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

export const deleteComment = (commentId, boardId) => {
    return function (dispatch) {
        Send({
            url: `/board/comment/delete/${commentId}`,
            method: 'delete',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(boardActions.deleteComment());
            dispatch(getSinglePost(boardId));
        })
        .catch(error => console.log(error));
    }
}