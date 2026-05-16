import {Link} from 'react-router';
import {FormattedTime} from 'react-intl';
import Button from 'react-bootstrap/Button';

const SessionLink = ({session}) => {

    return (
        <Button
            as={Link}
            to={`/catalog/sessions/${session.id}`}
            variant="outline-primary"
            size="sm"
            className="rounded-pill fw-semibold px-3"
        >
            <FormattedTime value={new Date(session.startTime)}/>
            <i className="fa-solid fa-chevron-right ms-2"></i>
        </Button>
    );
};

export default SessionLink;
