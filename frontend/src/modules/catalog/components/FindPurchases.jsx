import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router';

import backend from '../../../backend';
import * as actions from '../actions';

const FindPurchases = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {

        const findPurchases = async criteria => {
            const response = await backend.catalogService.findPurchases(criteria);
            if (response.ok) {
                dispatch(actions.findPurchasesCompleted({criteria, result: response.payload}));
            }
        }

        dispatch(actions.clearPurchaseSearch());
        navigate('/catalog/find-purchases-result');
        findPurchases({page: 0});

    }, [dispatch, navigate]);

    return null;

}

export default FindPurchases;
