import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';

import backend from '../../../backend';
import {Pager} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';
import Purchases from './Purchases';

const FindPurchasesResult = () => {

    const purchaseSearch = useSelector(selectors.getPurchaseSearch);
    const dispatch = useDispatch();

    const handleBackNext = async (backClicked, criteria) => {

        dispatch(actions.clearPurchaseSearch());

        const newCriteria = backClicked ?
            {page: criteria.page - 1} :
            {page: criteria.page + 1};

        const response = await backend.catalogService.findPurchases(newCriteria);

        if (response.ok) {
            dispatch(actions.findPurchasesCompleted({criteria: newCriteria, result: response.payload}));
        }

    }

    if (!purchaseSearch) {
        return null;
    }

    if (purchaseSearch.result.items.length === 0) {
        return (
            <Alert variant="info">
                <FormattedMessage id="project.catalog.FindPurchasesResult.noPurchases"/>
            </Alert>
        );
    }

    return (
        <>
            <Purchases purchases={purchaseSearch.result.items}/>
            <Pager
                back={{
                    enabled: purchaseSearch.criteria.page >= 1,
                    onClick: () => handleBackNext(true, purchaseSearch.criteria)}}
                next={{
                    enabled: purchaseSearch.result.existMoreItems,
                    onClick: () => handleBackNext(false, purchaseSearch.criteria)}}/>
        </>
    );

}

export default FindPurchasesResult;
