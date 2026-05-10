import {appFetch} from './appFetch';

export const getBillboard = async date =>
    await appFetch('GET', `/cinema/movies?date=${date}`);

