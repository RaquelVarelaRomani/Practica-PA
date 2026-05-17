import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import backend from '../../../backend';
import {Errors} from '../../common';
import * as actions from '../actions';

const BuyTicketsForm = ({sessionId}) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [numberTickets, setNumberTickets] = useState(1);
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    const [formValidated, setFormValidated] = useState(false);
    let form;

    const handleSubmit = async event => {
        event.preventDefault();
        if (form.checkValidity()) {

            const response = await backend.catalogService.buyTickets(sessionId, numberTickets, creditCardNumber.trim());
            if (response.ok) {
                dispatch(actions.buyTicketsCompleted(response.payload.id));
                navigate('/catalog/purchase-completed');
            } else {
                setBackendErrors(response.payload);
            }
        } else {
            setBackendErrors(null);
            setFormValidated(true);
        }
    }
    return (
        <div className="col-md-10 mx-auto mt-3">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <Card className="bg-light border-dark">
                <Card.Header as="h5">
                    <FormattedMessage id="project.catalog.BuyTicketsForm.title"/>
                </Card.Header>
                <Card.Body>
                    <Form ref={node => form = node}
                          validated={formValidated} noValidate
                          onSubmit={e => handleSubmit(e)}>
                        <Form.Group as={Row} className="mb-3" controlId="numberTickets">
                            <Form.Label column md={3}>
                                <FormattedMessage id="project.global.fields.numberTickets"/>
                            </Form.Label>
                            <Col md={4}>
                                <Form.Control type="number"
                                              value={numberTickets}
                                              onChange={e => setNumberTickets(Number(e.target.value))}
                                              autoFocus
                                              required
                                              min="1"
                                              max="10"/>
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id="project.global.validator.incorrectNumberTickets"/>
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
                                    <FormattedMessage id="project.global.buttons.buy"/>
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );
}
        export default BuyTicketsForm;