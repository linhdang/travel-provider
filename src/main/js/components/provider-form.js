import React from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';


class ProviderForm extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        departure: '',
        arrival: '',
        from: '',
        to: '',
        price: '',
        capacity: ''
      }
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      this.setState({[event.target.name]: event.target.value});
    }
  
    handleSubmit(event) {
      event.preventDefault();
      console.log(this.state);
      const data = new FormData(event.target);
      fetch('/api/create-plan', {
        method: 'POST',
        body: data,
      }).then(response => response.json()).then(json => {
          alert("submitted successfully")
      });
    }
  
    render() {
      return (
        <div>
          <h2>Submit tour</h2>
          <Form onSubmit={this.handleSubmit}>
            <Form.Group as={Row} controlId="departure">
              <Form.Label column sm="2">
                Departure
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="departure" as="select" value={this.state.departure} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="arrival">
              <Form.Label column sm="2">
                Arrival
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="arrival" as="select" value={this.state.arrival} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="from">
              <Form.Label column sm="2">
                From
              </Form.Label>
              <Col sm="10">
                <Form.Control name="from" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="to">
              <Form.Label column sm="2">
                To
              </Form.Label>
              <Col sm="10">
                <Form.Control name="to" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="price">
              <Form.Label column sm="2">
                      Price
              </Form.Label>
              <Col sm="10">
                <InputGroup className="mb-3">
                  <Form.Control name="price" type="number" value={this.state.price} onChange={this.handleChange}>
                  </Form.Control>
                  <InputGroup.Append>
                      <InputGroup.Text>$</InputGroup.Text>
                      <InputGroup.Text>0.00</InputGroup.Text>
                  </InputGroup.Append>
                </InputGroup>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="capacity">
              <Form.Label column sm="2">
                  Capacity
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="capacity" type="number" value={this.state.capacity}
                                onChange={this.handleChange}>
                  </Form.Control>
              </Col>
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
        </div>
      );
    }
  }

  export default ProviderForm;
