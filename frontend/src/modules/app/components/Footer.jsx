import {FormattedMessage} from 'react-intl';
import Container from 'react-bootstrap/Container';

const Footer = () => {
    const colors = {
        azulPrincipal: '#1A36FF',  // Azul de la base
        celesteCinepa: '#33B3F8',  // Celeste para la línea de arriba
        blancoLimpio: '#FFFFFF'
    };
    return (
        <footer
            className="border-top text-center py-3 mt-auto"
            style={{
                backgroundColor: colors.azulPrincipal,
                borderColor: colors.celesteCinepa,
                color: colors.blancoLimpio
            }}
        >
            <Container>
                <FormattedMessage id="project.app.Footer.text"/>
            </Container>
        </footer>
    );
};

export default Footer;
