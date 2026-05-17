import {useState, useEffect} from 'react';
import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';

import backend from '../../../backend';
import {Pager} from '../../common';
import Purchases from './Purchases';

const FindPurchases = () => {

    const [result, setResult] = useState(null);
    const [page, setPage] = useState(0);

    useEffect(() => {

        const findPurchases = async () => {
            const response = await backend.catalogService.findPurchases({page});
            if (response.ok) {
                setResult(response.payload);
            }
        }

        findPurchases();

    }, [page]);

    if (!result) {
        return null;
    }

    if (result.items.length === 0) {
        return (
            <Alert variant="info">
                <FormattedMessage id="project.catalog.FindPurchasesResult.noPurchases"/>
            </Alert>
        );
    }

    return (
        <>
            <Purchases purchases={result.items}/>
            <Pager
                back={{
                    enabled: page >= 1,
                    onClick: () => setPage(page - 1)}}
                next={{
                    enabled: result.existMoreItems,
                    onClick: () => setPage(page + 1)}}/>
        </>
    );

}

export default FindPurchases;
