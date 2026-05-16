import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import MovieLink from './MovieLink';
import SessionLink from './SessionLink';

const Movies = ({movies}) => {

    if (!movies) {
        return null;
    }

    if (movies.length === 0) {
        return (
            <Alert variant="info">
                <FormattedMessage id="project.catalog.Billboard.noMovies"/>
            </Alert>
        );
    }

    return (
        <div className="mt-4">
            <Row className="g-4 justify-content-center">
                {movies.map(movie =>
                    <Col key={movie.id} xs={12} sm={6} md={4} className="d-flex">
                        <Card className="w-100 border border-light shadow-sm rounded-4 bg-white">
                            <Card.Body className="p-4 text-center d-flex flex-column justify-content-between">

                                <div className="mb-4">
                                    <h5 className="fw-bold fs-5 d-flex align-items-center justify-content-center mb-0">
                                        <MovieLink id={movie.id} title={movie.title} />
                                    </h5>
                                </div>

                                <div className="d-flex flex-wrap gap-2 justify-content-center align-items-center">
                                    {movie.sessions.map((session) =>
                                        <div key={session.id} className="session-pill-container">
                                            <SessionLink session={session}/>
                                        </div>
                                    )}
                                </div>

                            </Card.Body>
                        </Card>
                    </Col>
                )}
            </Row>
        </div>
    );
}

export default Movies;
