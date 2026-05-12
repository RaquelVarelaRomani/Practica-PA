import {appFetch} from './appFetch';

export const getBillboard = async date =>
    await appFetch('GET', `/cinema/movies?date=${date}`);

export const findMovieById = async id =>
    await appFetch('GET', `/cinema/movies/${id}`);

export const findSessionById = async id =>
    await appFetch('GET', `/cinema/sessions/${id}`);
