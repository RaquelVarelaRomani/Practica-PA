import {useState} from 'react';
import {FormattedMessage, useIntl} from 'react-intl';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import backend from '../../../backend';
import {Errors, Success} from '../../common';

const DeliverTickets = () => {

    const intl = useIntl();
    const [purchaseId, setPurchaseId] = useState('');
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [formValidated, setFormValidated] = useState(false);
    let form;

    const handleSubmit = async event => {
        event.preventDefault();

        if (form.checkValidity()) {
            const response = await backend.catalogService.deliverTickets(Number(purchaseId), creditCardNumber.trim());

                if (response.ok) {
                    setBackendErrors(null);
                    setSuccessMessage(intl.formatMessage({id: 'project.catalog.DeliverTickets.success'}));
                    setPurchaseId('');
                    setCreditCardNumber('');
                    setFormValidated(false);
                } else {
                    setSuccessMessage(null);
                    setBackendErrors(response.payload);
                }
        } else {
            setBackendErrors(null);
            setSuccessMessage(null);
            setFormValidated(true);
        }
    }
    return (
        <div className="col-md-10 mx-auto">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <Success message={successMessage} onClose={() => setSuccessMessage(null)}/>
            <Card className="bg-light border-dark">
                <Card.Header as="h5">
                    <FormattedMessage id="project.catalog.DeliverTickets.title"/>
                </Card.Header>
                <Card.Body>
                    <Form ref={node => form = node} validated={formValidated} noValidate onSubmit={e => handleSubmit(e)}>
                        <Form.Group as={Row} className="mb-3" controlId="purchaseId">
                            <Form.Label column md={3}>
                                <FormattedMessage id="project.global.fields.purchaseId"/>
                            </Form.Label>
                            <Col md={4}>
                                <Form.Control type="number"
                                              value={purchaseId}
                                              onChange={e => setPurchaseId(e.target.value)}
                                              autoFocus
                                              required
                                              min="1"/>
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id="project.global.validator.required"/>
                                </Form.Control.Feedback>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} className="mb-3" controlId="creditCardNumber">
                            <Form.Label column md={3}>
                                <FormattedMessage id="project.global.fields.creditCardNumber"/>
                            </Form.Label>
                            <Col md={4}>
                                <Form.Control type="text"
                                              value={creditCardNumber}
                                              onChange={e => setCreditCardNumber(e.target.value)}
                                              required
                                              minLength="16"
                                              maxLength="16"
                                              pattern="[0-9]{16}"/>
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id="project.global.validator.creditCardNumber"/>
                                </Form.Control.Feedback>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row}>
                            <Col md={{span: 4, offset: 3}}>
                                <Button type="submit">
                                    <FormattedMessage id="project.catalog.DeliverTickets.submit"/>
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );
}
export default DeliverTickets;