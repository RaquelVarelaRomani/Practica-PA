import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';
import {getToday} from './dateUtils';

const initialState = {
    movies: null,
    billboardDate: getToday(),
    lastPurchaseId: null
};

const movies = (state = initialState.movies, action) => {
    switch (action.type) {
        case actionTypes.GET_BILLBOARD_COMPLETED:
            return action.movies;
        case actionTypes.CLEAR_BILLBOARD:
            return initialState.movies;
        default:
            return state;
    }
}

const billboardDate = (state = initialState.billboardDate, action) => {
    switch (action.type) {
        case actionTypes.CLEAR_BILLBOARD:
            return action.billboardDate;
        default:
            return state;
    }
}

const lastPurchaseId = (state = initialState.lastPurchaseId, action) => {
    switch (action.type) {
        case actionTypes.BUY_TICKETS_COMPLETED:
            return action.purchaseId;
        default:
            return state;
    }
}

const reducer = combineReducers({
    movies,
    billboardDate,
    lastPurchaseId
});

export default reducer;
