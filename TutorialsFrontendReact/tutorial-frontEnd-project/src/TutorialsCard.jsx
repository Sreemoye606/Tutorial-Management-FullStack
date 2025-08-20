import React, { useState } from 'react'
import axios from 'axios';

const TutorialsCard = ({id, title, description, published,onReload}) => {

  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState(
    {
      title : title,
      description : description,
      published : published
    }
  );

  const handleDelete = async ()=>{
    const delurl = `http://localhost:8888/api/tutorials/${id}`;

    const confirmDelete = window.confirm(`Are you sure you want to delete "${title}"?`);
    if (!confirmDelete) return;

    try {
      await axios.delete(delurl);
      onReload();
      
    } catch (error) {
      console.log("error ocurred", error);
      alert("couldn't delete tutorial")
    }
  };

  // edit function

  const handleEditClick =()=>{
    setIsEditing(true);
  };

  const handleChange =(e)=>{
    const { name, value, type, checked} = e.target;

    setFormData( (prev) => ({
      ...prev,
      [name] : type === "checkbox" ? checked : value,
    })
    );
  };

  const handleCancel =()=>{
    setFormData({title, description, published });

    setIsEditing(false);
  };

  const handleUpdate = async () => {
    const updateurl = `http://localhost:8888/api/tutorials/${id}`;

    try {
      await axios.put(updateurl, formData);
      onReload();
      console.log("tutorial updated successfully!");
      alert("tutorial updated successfully!");
      
      setIsEditing(false);
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
  };

  return (
    <div className="bg-white shadow-md rounded-2xl p-4 border hover:shadow-lg transition-shadow duration-200">
      {isEditing ? (
        <>
        {/* edit mode */}
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            className="text-xl font-semibold mb-2 w-full border p-2 rounded"
          />
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
            className="text-gray-700 mb-3 w-full border p-2 rounded resize-none"
          />
          <label className="block mb-3">
            <input
              type="checkbox"
              name="published"
              checked={formData.published}
              onChange={handleChange}
              className="mr-2"
            />
            Published
          </label>
          <div className="flex justify-end gap-2">
            <button
              className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded-lg"
              onClick={handleUpdate}
            >
              Save
            </button>
            <button
              className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded-lg"
              onClick={handleCancel}
            >
              Cancel
            </button>
          </div>
        </>

        ) : (

        <>
        {/* display mode */}
         <h2 className="text-xl font-semibold mb-2">{title}</h2>
          
          <div className="text-gray-700 mb-3 h-24 overflow-hidden group-hover:overflow-y-auto pr-1 scrollbar-thin scrollbar-thumb-gray-400 scrollbar-track-transparent break-words">
            {description}
          </div>

          <span className={`inline-block px-3 py-1 text-sm font-medium rounded-full ${
            published ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
          }`}>
              {published ? 'Published' : 'Unpublished'}
          </span>

          <div className="mt-4 flex justify-end gap-2">
            <button 
            className="px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg shadow-sm transition duration-200"
            onClick={handleEditClick}
            >
            Edit
            </button>
            <button 
            className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded-lg shadow-sm transition duration-200"
            onClick={handleDelete}>
            Delete
            </button>
          </div> 

          </>
        )}
    </div>
  )
}

export default TutorialsCard