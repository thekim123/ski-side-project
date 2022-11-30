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
            //dispatch(loadPosts());
        })
        .catch((error) => console.log(error));
    }
}