import {FormattedMessage} from 'react-intl';
import Container from 'react-bootstrap/Container';

const Footer = () => {
    return (
        <footer className="bg-primary border-top text-white text-center py-3 mt-auto">
            <Container className="text-white">
                <FormattedMessage id="project.app.Footer.text"/>
            </Container>
        </footer>
    );
};

export default Footer;
