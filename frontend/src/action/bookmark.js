import Send from '../components/common/Send';
import { bookmarkActions } from '../slice/bookmark';

export const loadBookmarks = () => {
    return function (dispatch) {
        Send({
            url: '/bookmark/mine',
            method: 'get',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(bookmarkActions.getBookmarks(resp.data.data));
        })
        .catch(error => console.log(error));
    }
}

export const addBookmark = (resortId) => {
    return function (dispatch) {
        Send({
            url: `/bookmark/${resortId}`,
            method: 'post',
        }).then((resp) => {
            console.log("resp", resp);
            dispatch(bookmarkActions.addBookmark());
            //dispatch(loadBookmarks());
        })
        .catch((error) => console.log(error));
    }
}

export const deleteBookmark = (resortId) => {
    return function (dispatch) {
        Send({
            url: `/bookmark/${resortId}`,
            method: 'delete',
        }).then((resp) => {
            console.log(resp);
            dispatch(bookmarkActions.deleteBookmark());
        })
        .catch(error => console.log(error));
    }
}