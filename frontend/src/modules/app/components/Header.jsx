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

    return (

        <Navbar expand="lg" bg="primary" data-bs-theme="dark" className="border-bottom">
            <Container fluid>
                <Navbar.Brand as={NavLink} to="/" className="fw-bold text-white">
                    CinePA
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarSupportedContent" className="mb-3"/>
                <Navbar.Collapse id="navbarSupportedContent">

                    {userName ? (
                        <Nav className="ms-auto align-items-center">
                            <NavLink
                                to="/"
                                end
                                className={({isActive}) => `nav-link text-white ${isActive ? 'active fw-bold' : ''}`}
                            >
                                <FormattedMessage id="project.catalog.Billboard.title"/>
                            </NavLink>

                            {isViewer &&
                                <NavLink
                                    to="/catalog/find-purchases"
                                    className={({isActive}) => `nav-link text-white ${isActive ? 'active fw-bold' : ''}`}
                                >
                                    <FormattedMessage id="project.catalog.Header.purchases"/>
                                </NavLink>
                            }
                            {isTicketSeller &&
                                <NavLink
                                    to="/catalog/deliver-tickets"
                                    className={({isActive}) => `nav-link text-white ${isActive ? 'active fw-bold' : ''}`}
                                >
                                    <FormattedMessage id="project.catalog.Header.deliverTickets"/>
                                </NavLink>
                            }
                            <NavDropdown
                                title={
                                    <span className="d-inline-flex align-items-center gap-2 text-white">
                                        <i className="fa-solid fa-user"></i>
                                        <span>{userName}</span>
                                    </span>
                                }
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
                        <Nav className="ms-auto align-items-center">
                            <NavLink
                                to="/"
                                end
                                className={({isActive}) => `nav-link text-white ${isActive ? 'active fw-bold' : ''}`}
                            >
                                <FormattedMessage id="project.catalog.Billboard.title"/>
                            </NavLink>

                            <NavLink
                                id="loginLink"
                                to="/users/login"
                                className={({isActive}) => `nav-link text-white ${isActive ? 'active fw-bold' : ''}`}
                            >
                                <FormattedMessage id="project.users.Login.title"/>
                            </NavLink>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>

    );

};

export default Header;
