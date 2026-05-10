import * as actionTypes from './actionTypes';

export const getBillboardCompleted = movies => ({
    type: actionTypes.GET_BILLBOARD_COMPLETED,
    movies
});

export const clearBillboard = billboardDate => ({
    type: actionTypes.CLEAR_BILLBOARD,
    billboardDate
});

