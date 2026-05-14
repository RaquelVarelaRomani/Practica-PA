import {FormattedDate, FormattedMessage, FormattedNumber, FormattedTime} from 'react-intl';
import Table from 'react-bootstrap/Table';

const Purchases = ({purchases}) => (

    <Table striped hover responsive>
        <thead>
            <tr>
                <th>
                    <FormattedMessage id="project.global.fields.purchaseDate"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.purchaseId"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.movie"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.numberTickets"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.totalPrice"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.sessionDate"/>
                </th>
                <th>
                    <FormattedMessage id="project.global.fields.delivered"/>
                </th>
            </tr>
        </thead>
        <tbody>
            {purchases.map(purchase =>
                <tr key={purchase.id}>
                    <td>
                        <FormattedDate value={new Date(purchase.purchaseDate)}/>{' '}
                        <FormattedTime value={new Date(purchase.purchaseDate)}/>
                    </td>
                    <td>{purchase.id}</td>
                    <td>{purchase.movieTitle}</td>
                    <td>{purchase.numberTickets}</td>
                    <td>
                        <FormattedNumber value={purchase.totalPrice} style="currency" currency="EUR"/>
                    </td>
                    <td>
                        <FormattedDate value={new Date(purchase.sessionStartTime)}/>{' '}
                        <FormattedTime value={new Date(purchase.sessionStartTime)}/>
                    </td>
                    <td>
                        <FormattedMessage id={purchase.delivered ?
                            "project.global.fields.yes" :
                            "project.global.fields.no"}/>
                    </td>
                </tr>
            )}
        </tbody>
    </Table>

);

export default Purchases;
