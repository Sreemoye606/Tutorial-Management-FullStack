import { useState, useEffect } from 'react'
import axios from 'axios'
import TutorialList from './TutorialList'
import AddTutorial from './AddTutorial'
import TutorialSearch from './TutorialSearch'

function App() {

  const [tutorials, setTutorials] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [published, setPublished] = useState('all');

  const [reload, setReload] = useState(false);

  

  const handleReload = () => {
    setReload(prev => !prev);
  };
  

  const fetchTutorials = async ()=> {
    try {
      let url = '';
      const hasKeyword = keyword.trim() !== '';
      const hasPublished = published !== 'all';


      if (!hasKeyword && !hasPublished) {
        // fallback to all tutorials
        url = 'http://localhost:8888/api/tutorials';
        

      } 
      else if (hasKeyword && !hasPublished){
        url = `http://localhost:8888/api/tutorials/search?keyword=${keyword}`;
      }
      else if (hasPublished && !hasKeyword){
        url = `http://localhost:8888/api/tutorials/search?published=${published}`;
      }
      else {
        url = `http://localhost:8888/api/tutorials/search?keyword=${keyword}&published=${published}`
      }

      const response = await axios.get(url);
      setTutorials(response.data);

    }catch (error) {
      console.log("could not fetch search", error)
    }
  };
 
  useEffect(() => {
    fetchTutorials();
  }, [reload, keyword, published]);


  return (
    <>
    {/* <h1 className="text-xl font-bold mb-4">Tutorial Management System</h1>
      <AddTutorial onAdd={handleReload}/>

      <TutorialSearch 
      keyword={keyword}
      setKeyword={setKeyword}
      published={published}
      setPublished={setPublished}/>

      <TutorialList tutorials={tutorials} onReload={fetchTutorials} /> */}

      <>
  <div className="min-h-screen bg-gray-100 p-4">
    <div className="max-w-5xl mx-auto bg-white shadow-md rounded-xl p-6">
      <h1 className="text-2xl font-bold text-center text-blue-700 mb-6">
        Tutorial Management System
      </h1>

      {/* Add Tutorial */}
      <div className="mb-6">
        <AddTutorial onAdd={handleReload} />
      </div>

      {/* Search and Filter */}
      <div className="mb-6">
        <TutorialSearch
          keyword={keyword}
          setKeyword={setKeyword}
          published={published}
          setPublished={setPublished}
        />
      </div>

      {/* Tutorial List */}
      <TutorialList tutorials={tutorials} onReload={fetchTutorials} />
    </div>
  </div>
</>


    </>
  )
}

export default App
