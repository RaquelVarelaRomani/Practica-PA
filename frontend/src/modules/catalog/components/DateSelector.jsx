import {useIntl} from 'react-intl';
import Form from 'react-bootstrap/Form';

import {toDateValue} from '../dateUtils';

const getDates = () => {
    const today = new Date();

    return Array.from({length: 7}, (_, index) => {
        const date = new Date(today);
        date.setDate(today.getDate() + index);
        return date;
    });
}

const DateSelector = props => {

    const intl = useIntl();

    return (
        <Form.Select {...props}>
            {getDates().map(date =>
                <option key={toDateValue(date)} value={toDateValue(date)}>
                    {intl.formatDate(date)}
                </option>
            )}
        </Form.Select>
    );

}

export default DateSelector;
