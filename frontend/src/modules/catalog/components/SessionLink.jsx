import {Link} from 'react-router';
import {FormattedTime} from 'react-intl';

const SessionLink = ({session}) => {

    return (
        <Link to={`/catalog/sessions/${session.id}`} className="cinepa-session-btn">
            <div className="d-flex align-items-center gap-2">
                <span className="session-time">
                    <FormattedTime value={new Date(session.startTime)}/>
                </span>

                <span className="session-arrow">
                    <i className="fa-solid fa-chevron-right"></i>
                </span>
            </div>

            <style>{`
                .cinepa-session-btn {
                    display: inline-block;
                    padding: 8px 20px;
                    background-color: #FFFFFF;
                    color: #2D3748 !important;
                    border: 2px solid #E2E8F0;
                    text-decoration: none !important;
                    font-weight: 600;
                    font-size: 0.95rem;
                    border-radius: 12px;
                    transition: all 0.2s ease-in-out;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.02);
                }

                .session-time {
                    color: #1A202C;
                }

                .session-arrow {
                    color: #1A36FF; 
                    font-size: 0.8rem;
                    margin-left: 2px;
                    transition: transform 0.2s ease;
                }

                .cinepa-session-btn:hover {
                    border-color: #1A36FF;
                    background-color: #F8FAFC;
                    transform: translateY(-2px);
                    box-shadow: 0 6px 15px rgba(26, 54, 255, 0.08);
                }

                .cinepa-session-btn:hover .session-arrow {
                    transform: translateX(3px);
                }
            `}</style>
        </Link>
    );
};

export default SessionLink;