import {useState, useEffect} from 'react';
import {FormattedMessage} from 'react-intl';
import {Navigate, useParams} from 'react-router';
import Card from 'react-bootstrap/Card';

import backend from '../../../backend';
import {BackLink} from '../../common';

const MovieDetails = () => {

    const [movie, setMovie] = useState(null);
    const {movieId} = useParams();
    const numericMovieId = Number(movieId);
    const isInvalidMovieId = Number.isNaN(numericMovieId);

    useEffect(() => {

        if (isInvalidMovieId) {
            return;
        }

        const findMovieById = async movieId => {
            const response = await backend.catalogService.findMovieById(movieId);
            if (response.ok) {
                setMovie(response.payload);
            }
        }

        findMovieById(numericMovieId);

    }, [isInvalidMovieId, numericMovieId]);

    if (isInvalidMovieId) {
        return <Navigate to="/" replace/>;
    }

    if (!movie) {
        return null;
    }

    return (
        <>
            <BackLink/>

            <Card className="text-center mt-3">
                <Card.Body>
                    <Card.Title>{movie.title}</Card.Title>
                    <Card.Text>{movie.summary}</Card.Text>
                    <Card.Text className="fw-bold">
                        <FormattedMessage id="project.global.fields.duration"/>:{' '}
                        {movie.durationMinutes}{' '}
                        <FormattedMessage id="project.global.fields.minutes"/>
                    </Card.Text>
                </Card.Body>
            </Card>
        </>
    );

}

export default MovieDetails;
