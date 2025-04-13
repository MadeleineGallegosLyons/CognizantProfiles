import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';

export function HeaderLoggedIn() {
    return (
        <>
        <Navbar className="p-3 mb-2 text-white">
            <Container>
                <Navbar.Brand href="#home">
                    <img
                        alt=""
                        src={require('./images/cogLogo1.jpg')}
                        width="20%"
                        className="d-inline-block align-top"
                    />{' '}
                    Cognizant Profiles
                </Navbar.Brand>
            </Container>
            </Navbar>
            </>
    );
}