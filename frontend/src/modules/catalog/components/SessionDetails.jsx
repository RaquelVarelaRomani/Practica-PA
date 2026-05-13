import {useState, useEffect} from 'react';
import {FormattedDate, FormattedMessage, FormattedNumber, FormattedTime} from 'react-intl';
import {useParams} from 'react-router';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

import backend from '../../../backend';
import {BackLink, Errors} from '../../common';
import users from '../../users';
import {useSelector} from 'react-redux';
import BuyTicketsForm from './BuyTicketsForm';
import MovieLink from './MovieLink';

const SessionDetails = () => {

    const [session, setSession] = useState(null);
    const [backendErrors, setBackendErrors] = useState(null);
    const isViewer = useSelector(users.selectors.isViewer);
    const {id} = useParams();
    const sessionId = Number(id);

    useEffect(() => {

        const findSessionById = async sessionId => {
            if (!Number.isNaN(sessionId)) {
                const response = await backend.catalogService.findSessionById(sessionId);
                if (response.ok) {
                    setSession(response.payload);
                } else {
                    setBackendErrors(response.payload);
                }
            }
        }

        findSessionById(sessionId);

    }, [sessionId]);

    return (
        <>
            <BackLink/>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            {session &&
                <Card className="text-center mt-3">
                    <Card.Body>
                        <Card.Title>
                            <MovieLink id={session.movieId} title={session.movieTitle}/>
                        </Card.Title>
                    </Card.Body>
                    <ListGroup variant="flush">
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.duration"/>:{' '}
                            {session.durationMinutes}{' '}
                            <FormattedMessage id="project.global.fields.minutes"/>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.price"/>:{' '}
                            <FormattedNumber value={session.price} style="currency" currency="EUR"/>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.date"/>:{' '}
                            <FormattedDate value={new Date(session.startTime)}/>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.time"/>:{' '}
                            <FormattedTime value={new Date(session.startTime)}/>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.room"/>:{' '}
                            {session.roomName}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <FormattedMessage id="project.global.fields.availableTickets"/>:{' '}
                            {session.availableTickets}
                        </ListGroup.Item>
                    </ListGroup>
                </Card>
            }
            {session && isViewer && session.availableTickets > 0 &&
                <BuyTicketsForm sessionId={session.id} availableTickets={session.availableTickets}/>
            }
        </>
    );

}

export default SessionDetails;