import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import backend from '../../../backend';
import DateSelector from './DateSelector';
import Movies from './Movies';
import * as actions from '../actions';
import * as selectors from '../selectors';

const Billboard = () => {

    const movies = useSelector(selectors.getMovies);
    const billboardDate = useSelector(selectors.getBillboardDate);
    const dispatch = useDispatch();

    const handleBillboardDateChange = async date => {
        dispatch(actions.clearBillboard(date));
        const response = await backend.catalogService.getBillboard(date);
        if (response.ok) {
            dispatch(actions.getBillboardCompleted(response.payload));
        }
    }

    return (
        <div>
            <h4>
                <FormattedMessage id="project.catalog.Billboard.title"/>
            </h4>
            <DateSelector id="billboardDate" className="mb-2 w-auto"
                value={billboardDate} onChange={e => handleBillboardDateChange(e.target.value)}/>
            <Movies movies={movies}/>
        </div>
    );

}

export default Billboard;

