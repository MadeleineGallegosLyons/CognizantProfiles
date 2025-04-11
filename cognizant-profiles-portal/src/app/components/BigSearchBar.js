import { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
function BigSearchBar() {
    const [searchText, setSearchText] = useState('');
  return (
    <>
    <InputGroup className="mb-3">
        <InputGroup.Text id="basic-addon2" style={styles.cogBlue}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-search" viewBox="0 0 16 16">
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
            </svg>
        </InputGroup.Text>
        <Form.Control type="text" placeholder = "Search Text Here" size="lg" aria-label="Search Text Here" aria-describedby="basic-addon2" value = {searchText} onChange = {e => setSearchText(e.target.value)}/>            
        <Button style={styles.cogBlue} variant="outline-secondary" size="lg" id="button-addon2">Search</Button>
    </InputGroup>
    </>
  );
}

const styles = {
    cogBlue: {
        backgroundColor: "#000048",
        color: "white"
    },
}

export default BigSearchBar;