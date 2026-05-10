import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';
import Table from 'react-bootstrap/Table';

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
        <Table striped hover>
            <thead>
                <tr>
                    <th>
                        <FormattedMessage id="project.global.fields.movie"/>
                    </th>
                    <th>
                        <FormattedMessage id="project.global.fields.sessions"/>
                    </th>
                </tr>
            </thead>
            <tbody>
                {movies.map(movie =>
                    <tr key={movie.id}>
                        <td>
                            <MovieLink id={movie.id} title={movie.title}/>
                        </td>
                        <td>
                            {movie.sessions.map((session, index) =>
                                <span key={session.id}>
                                    {index > 0 && ', '}
                                    <SessionLink session={session}/>
                                </span>
                            )}
                        </td>
                    </tr>
                )}
            </tbody>
        </Table>
    );

}

export default Movies;

