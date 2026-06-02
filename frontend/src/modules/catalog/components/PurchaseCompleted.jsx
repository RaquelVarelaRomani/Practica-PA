import {useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';

import * as selectors from '../selectors';

const PurchaseCompleted = () => {
    const purchaseId = useSelector(selectors.getLastPurchaseId);
    if (!purchaseId) {
        return null;
    }
    return (
        <Alert variant="success">
            <FormattedMessage id="project.catalog.PurchaseCompleted.purchaseGenerated"/>:{' '}
            <span id="purchaseId">{purchaseId}</span>
        </Alert>
    );
}

export default PurchaseCompleted;