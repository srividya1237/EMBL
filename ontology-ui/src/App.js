import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [data, setData] = useState([]);
  const [ontologyId, setontologyId] = useState("");
  const [fetchedontology, setFetchedontology] = useState(null);
  const [fetchError, setFetchError] = useState(null);

  const getontologyById = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(`http://localhost:8080/ontology/getDetails/${ontologyId}`);
      setFetchedontology(response.data);
    } catch (error) {
      console.error("Error fetching ontology:", error);
      setFetchedontology(null);
      setFetchError(error?.response.data.message);
    }
  };

  return (
    <div className="App">
      

      {/* Form to fetch ontology by ID */}
      <form onSubmit={getontologyById}>
        <input
          type="text"
          name="ontologyId"
          placeholder="ontology ID"
          value={ontologyId}
          onChange={(event) => setontologyId(event.target.value)}
        />
        <button type="submit">Get ontology</button>
      </form>

      { fetchedontology==null ? (
        <h1>{fetchError}</h1>
      ) : (
        <>
          <h1>{fetchedontology.data.title}</h1>
          <p>
            <strong>Ontology ID:</strong> {fetchedontology.data.ontologyId}
          </p>
          <p>
            <strong>Description:</strong> {fetchedontology.data.description}
          </p>
          <div>
            <strong>Definition Properties:</strong>
            <ul>
              {fetchedontology.data.definitionProperties.map((property, index) => (
                <li key={index}>{property}</li>
              ))}
            </ul>
          </div>
          <div>
            <strong>Synonym Properties:</strong>
            <ul>
              {fetchedontology.data.synonymProperties.map((property, index) => (
                <li key={index}>{property}</li>
              ))}
            </ul>
          </div>
        </>
      )}
      

     
    </div>
  );
}

export default App;