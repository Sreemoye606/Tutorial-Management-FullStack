import React from 'react'
import { useState, useEffect } from 'react';
import axios from 'axios';
import TutorialsCard from './TutorialsCard';

function TutorialList({tutorials, onReload}) {


  if (tutorials.length === 0) return <p className="p-4">No tutorials found.</p>;
  return (
    <div className="grid gap-4 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 p-4">
      {
        tutorials.map(
          (tut)=>(
          <TutorialsCard
          key={tut.id}
          id={tut.id}
          title={tut.title}
          description={tut.description}
          published={tut.published}
          onReload={onReload}

          />
        )
        )
        }
    </div>
  )
}

export default TutorialList
