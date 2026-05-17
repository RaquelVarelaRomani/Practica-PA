import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router';
import Container from 'react-bootstrap/Container';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import users from '../../users';
import {DeliverTickets, FindPurchases, MovieDetails, PurchaseCompleted, SessionDetails} from '../../catalog';

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const isViewer = useSelector(users.selectors.isViewer);
    const isTicketSeller = useSelector(users.selectors.isTicketSeller);
    
   return (
       <Container className="my-4 justify-content-center flex-grow-1">
            <AppGlobalComponents/>
            <Routes>
                <Route path="/*" element={<Home/>}/>
                <Route path="/catalog/movies/:movieId" element={<MovieDetails/>}/>
                <Route path="/catalog/sessions/:id" element={<SessionDetails/>}/>
                {loggedIn && <Route path = "/catalog/purchase-completed" element = {<PurchaseCompleted/>}/>}
                {isViewer && <Route path="/catalog/find-purchases" element={<FindPurchases/>}/>}
                {isTicketSeller && <Route path="/catalog/deliver-tickets" element={<DeliverTickets/>}/>}
                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
            </Routes>
       </Container>

    );

};

export default Body;
