import {appFetch} from './appFetch';

export const getBillboard = async date =>
    await appFetch('GET', `/cinema/movies?date=${date}`);

export const findMovieById = async id =>
    await appFetch('GET', `/cinema/movies/${id}`);

export const findSessionById = async id =>
    await appFetch('GET', `/cinema/sessions/${id}`);

export const buyTickets = async (sessionId, numberTickets, creditCardNumber) =>
    await appFetch('POST', '/cinema/buy', {sessionId, numberTickets, creditCardNumber});

export const findPurchases = async ({page}) =>
    await appFetch('GET', `/cinema/purchases?page=${page}`);
