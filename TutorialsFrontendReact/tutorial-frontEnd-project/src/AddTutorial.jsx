import React from 'react'
import { useState } from 'react'
import axios from 'axios';

const AddTutorial = ({onAdd}) => {
    const [tutorial, setTutorial] = useState(
        {
            id : '',
            title : '',
            description : '',
            published : false
        }
    );

    const [isAdding, setIsAdding] = useState(false);

    const handleAddingClick =()=>{
    setIsAdding(true);
    };

    const handleCancel =()=>{
    setTutorial({d:'',title :'', description :'', published : false });

    setIsAdding(false);
     };

    const postUrl = "http://localhost:8888/api/tutorials";
    // const postUrl = "http://localhost:8888";

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {
            const res = await axios.post(postUrl, tutorial, {
                headers : {
                    'Content-Type' : 'application/json'
                }
            }
            );
            console.log("tutorial added : ", res.data);
            alert('Tutorial added successfully!');

            setIsAdding(false);
            
            onAdd(); // Notify parent to reload TutorialList
            
            //reset form after submit
            setTutorial({id : '',title : '',description : '',published : false});

        } catch (error) {
            if (error.response) {
                // Server responded with error
                const { message, code } = error.response.data;
                console.error("Bad Request:", message);
                alert(`Error: ${message}`);        
            }
            else {
                // Other unexpected error
                console.error("Error:", error.message);
                alert("Unexpected error occurred.");
            }
        }
    }

        

    const handleChange = (elem) => {
        const {name, value, type, checked} = elem.target;
        setTutorial({
            ...tutorial,[name] : type ==='checkbox'? checked : value
        });
    };


  return (
    <div>
        {isAdding? (
            // adding state
            <div className="p-4 bg-white shadow-md rounded-xl max-w-md mx-auto mt-6">
            
                <h2 className="text-xl font-bold mb-4">Add New Tutorial</h2>
                <form onSubmit={handleSubmit} className="space-y-3">
                <div>
                    <label className="block font-semibold">Title:</label>
                    <input 
                    type="text" 
                    name="title"
                    value={tutorial.title}
                    onChange={handleChange}
                    required
                    className="w-full p-2 border rounded"
                    />
                </div>
                <div>
                    <label className="block font-semibold">Description:</label>
                    <textarea
                    name="description"
                    value={tutorial.description}
                    onChange={handleChange}
                    required
                    className="w-full p-2 border rounded resize-none"
                    ></textarea>
                </div>
                <div className="flex items-center gap-2">
                <input
                    type="checkbox"
                    name="published"
                    checked={tutorial.published}
                    onChange={handleChange}
                    className="h-4 w-4"
                />
                <label htmlFor="published" className="font-semibold">Published?</label>
            </div>
            <div className="flex gap-3">
                <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                Add Tutorial
                </button>
                <button type="button" className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
                onClick={handleCancel}>
                Cancel
                </button>
            </div>
            
            </form>
        </div>
            
        ):(
            // display state
            <button
                className="flex items-center gap-2 bg-gradient-to-r from-blue-500 to-blue-700 text-white font-semibold px-5 py-2.5 rounded-full shadow-md hover:from-blue-600 hover:to-blue-800 transition-all duration-200"
                onClick={handleAddingClick}
                >
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="h-5 w-5"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                </svg>
                Add New Tutorial
                </button>

        )}

    </div>
  )
}

export default AddTutorial
