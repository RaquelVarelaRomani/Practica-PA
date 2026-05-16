import {useSelector} from 'react-redux';
import {NavLink} from 'react-router';
import {FormattedMessage} from 'react-intl';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Container from "react-bootstrap/Container";

import users from '../../users';

const Header = () => {

    const userName = useSelector(users.selectors.getUserName);
    const isViewer = useSelector(users.selectors.isViewer);
    const isTicketSeller = useSelector(users.selectors.isTicketSeller);

    const colors = {
        azulPrincipal: '#1A36FF',
        celesteCinepa: '#33B3F8',
        blancoLimpio: '#FFFFFF'
    };
    const getLinkStyle = (isActive) => ({
        color: colors.blancoLimpio,
        fontWeight: isActive ? '700' : '400',
        fontSize: isActive ? '1.15rem' : '1rem',
        transition: 'all 0.2s ease-in-out',
        display: 'inline-block'
    });
    return (

        <Navbar expand="lg" className="border-bottom" style={{ backgroundColor: colors.azulPrincipal, borderColor: colors.celesteCinepa }}>
            <Container fluid>
                <Navbar.Brand as={NavLink} to="/" style={{ color: colors.blancoLimpio, fontWeight: 'bold' }}>
                    CinePA
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarSupportedContent" className="mb-3" style={{ filter: 'invert(1)' }}/>
                <Navbar.Collapse id="navbarSupportedContent">

                    {userName ? (
                        <Nav className="ms-auto" style={{ alignItems: 'center' }}>
                            <NavLink
                                to="/"
                                end
                                className={({ isActive }) => `nav-link ${isActive ? 'active' : ''} link-hover-underline`}
                                style={({ isActive }) => getLinkStyle(isActive)}
                            >
                                <FormattedMessage id="project.catalog.Billboard.title"/>
                            </NavLink>

                            {isViewer &&
                                <NavLink
                                    to="/catalog/find-purchases"
                                    className={({ isActive }) => {
                                        const inResultPage = window.location.pathname === '/catalog/find-purchases-result';
                                        return `nav-link ${isActive || inResultPage ? 'active' : ''} link-hover-underline`;
                                    }}
                                    style={({ isActive }) => {
                                        const inResultPage = window.location.pathname === '/catalog/find-purchases-result';
                                        return getLinkStyle(isActive || inResultPage);
                                    }}
                                >
                                    <FormattedMessage id="project.catalog.Header.purchases"/>
                                </NavLink>
                            }
                            {isTicketSeller &&
                                <NavLink
                                    to="/catalog/deliver-tickets"
                                    className={({ isActive }) => `nav-link ${isActive ? 'active' : ''} link-hover-underline`}
                                    style={({ isActive }) => getLinkStyle(isActive)}
                                >
                                    <FormattedMessage id="project.catalog.Header.deliverTickets"/>
                                </NavLink>
                            }
                            <NavDropdown
                                title={<><span className="fa-solid fa-user" style={{ color: colors.blancoLimpio }}></span>&nbsp;<span style={{ color: colors.blancoLimpio }}>{userName}</span></>}
                                align="end"
                                id="user-dropdown"
                            >
                                <NavDropdown.Item as={NavLink} to="/users/update-profile">
                                    <FormattedMessage id="project.users.UpdateProfile.title"/>
                                </NavDropdown.Item>
                                <NavDropdown.Item as={NavLink} to="/users/change-password">
                                    <FormattedMessage id="project.users.ChangePassword.title"/>
                                </NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item as={NavLink} to="/users/logout">
                                    <FormattedMessage id="project.app.Header.logout"/>
                                </NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    ) : (
                        <Nav className="ms-auto" style={{ alignItems: 'center' }}>
                            <NavLink
                                to="/"
                                end
                                className={({ isActive }) => `nav-link ${isActive ? 'active' : ''} link-hover-underline`}
                                style={({ isActive }) => getLinkStyle(isActive)}
                            >
                                <FormattedMessage id="project.catalog.Billboard.title"/>
                            </NavLink>

                            <NavLink
                                to="/users/login"
                                className={({ isActive }) => `nav-link ${isActive ? 'active' : ''} link-hover-underline`}
                                style={({ isActive }) => getLinkStyle(isActive)}
                            >
                                <FormattedMessage id="project.users.Login.title"/>
                            </NavLink>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
            <style>{`
                .link-hover-underline:hover {
                    text-decoration: underline !important;
                    color: ${colors.blancoLimpio} !important;
                }
            `}</style>
        </Navbar>

    );

};

export default Header;
