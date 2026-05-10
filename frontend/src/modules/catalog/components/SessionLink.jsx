import {Link} from 'react-router';
import {FormattedTime} from 'react-intl';

const SessionLink = ({session}) => (
    <Link to={`/catalog/sessions/${session.id}`}>
        <FormattedTime value={new Date(session.startTime)}/>
    </Link>
);

export default SessionLink;

