import React from 'react';

function TutorialSearch({ keyword, setKeyword, published, setPublished }) {
  return (
    <div className="flex gap-2 mb-4 p-2">
      <input
        type="text"
        placeholder="Search by keyword"
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
        className="border p-2 rounded"
      />

      <select
        value={published}
        onChange={(e) => setPublished(e.target.value)}
        className="border p-2 rounded"
      >
        <option value="all">All</option>
        <option value="true">Published</option>
        <option value="false">Unpublished</option>
      </select>
    </div>
  );
}

export default TutorialSearch;
