import {Link} from 'react-router';

const MovieLink = ({id, title}) => (
    <Link to={`/catalog/movies/${id}`}>{title}</Link>
);

export default MovieLink;

