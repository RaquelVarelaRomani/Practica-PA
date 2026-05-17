import {Link} from 'react-router';

const MovieLink = ({id, title}) => (
    <Link to={`/catalog/movies/${id}`} className="text-decoration-none">{title}</Link>
);

export default MovieLink;

