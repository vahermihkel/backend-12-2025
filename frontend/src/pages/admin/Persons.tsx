import { useEffect, useState } from "react";
import useFetch from "../../hooks/useFetch";
import { Person } from "../../models/Person";

const backendUrl = import.meta.env.VITE_API_HOST;

function Persons() {
  const [persons, setPersons] = useState<Person[]>([]);
  const dbPersons = useFetch<Person>({endpoint: "persons"});

  useEffect(() => {
    setPersons(dbPersons);
  }, [dbPersons]);

  const changeAdmin = async(person: Person) => {
    const res = await fetch(backendUrl + "/change-admin" + "?personId=" + person.id, {
      "method": "PATCH",
      "headers": {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    });
    const json = await res.json();
    setPersons(json);
  }

  return (
   <div className='container'>
        <h2 className='text-center'>List of Persons</h2>
        <table className='table table-striped table-bordered'>
          <thead className='table-success'>
            <tr>
              <th>ID</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>PersonalCode</th>
              <th>Phone</th>
              <th>City + Street</th>
              <th>Change admin</th>
            </tr>
          </thead>
            <tbody>
              {
                persons.map(person =>
                  <tr key={person.id}>
                    <td>{person.id}</td>
                    <td>{person.firstName}</td>
                    <td>{person.lastName}</td>
                    <td>{person.email}</td>
                    <td>{person.personalCode}</td>
                    <td>{person.phone}</td>
                    <td>{person.address?.city}, {person.address?.street}</td>
                    <td>
                        {person.role === "SUPERADMIN" ?
                          <span>SUPERADMIN</span> :
                          <button onClick={() => changeAdmin(person)}>{person.role}</button>}
                    </td>
                  </tr>)
              }
            </tbody>
        </table>
    </div>
  )
}

export default Persons