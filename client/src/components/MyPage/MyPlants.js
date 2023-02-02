import styled from "styled-components";

import MyPlantInfo from "./MyPlantInfo";
import Cookie from "../../util/Cookie";
import PlantThumbnail from "./PlantThumbnail";

import { GrPrevious } from "react-icons/gr";
import { GrNext } from "react-icons/gr";
import { RiPlantLine } from "react-icons/ri";
import { ReactComponent as Cookiee } from "../../assets/svg/plus.svg";
import { useEffect, useState } from "react";
import axios from "axios";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  margin: 20px 0;

  @media screen and (max-width: 770px) {
    width: 360px;
  }
`;

const StyledMyPlantsDashBoard = styled.div`
  display: flex;
  width: 100%;

  .icon {
    width: 100%;
    height: 30%;
  }
`;

const StyledMoveButton = styled.div`
  width: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;

  &.hidden {
    opacity: 0.1;
  }
`

// const StyledPagination = styled.div`
//   display: flex;
//   justify-content: center;
//   align-items: center;
//   width: 100%;
//   background-color: white;
//   margin-bottom: 5px;

//   > div {
//     width: 13px;
//     height: 13px;
//     border-radius: 50%;
//     background-color: grey;
//     opacity: 0.8;
//     box-shadow: 2px 2px 2px #dbdbdb;
//     margin: 5px;
//   }

//   > div:nth-child(${props => props.curPage}) {
//     width: 15px;
//     height: 15px;
//     background-color: #D96848;
//   }
// `

const StyledListsContainer = styled.div`
  display: flex;
  width: 700px;
  align-items: center;
  overflow: hidden;

  @media screen and (max-width: 770px) {
      width: 360px;
    }
`;

const StyledMyPlantsItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 20px;
  .selected {
    border: 4px solid #ffb100;
  }
  .image-wrapper {
    width: 80px;
    height: 80px;
    border-radius: 70%;
    overflow: hidden;
    cursor: pointer;

    @media screen and (max-width: 770px) {
      width: 50px;
      height: 50px;
    }
  }
  .image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  p {
    display: flex;
    justify-content: center;
    width: 100%;
    font-size: 0.8em;

    @media screen and (max-width: 770px) {
      font-size: 10px;
    }
  }
`;

const StyledNoContents = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 550px;
  svg {
    width: 50px;
    height: 50px;
    margin: 25px 0;
    color: #374435;
  }
  p {
    display: flex;
    justify-content: center;
    width: 100%;
    font-size: 0.8em;
  }

  @media screen and (max-width: 770px) {
    width: 360px;

    svg {
      width: 30px;
      height: 30px;
    }
  }
`;

const MyPlants = ({ isOwnPage, currentView, setCurrentView, userInfo, currentPlantData, setCurrentPlantData, handleAddPlant, handleChange }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")

  const [myPlantsData, setMyPlantsData] = useState([]);
  const [isPanelOpened, setIsPanelOpened] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [curPage, setCurPage] = useState(1);

  const getMyPlantsData = () => {
    if (isOwnPage) {
      axios({
        method: "get",
        url: `${process.env.REACT_APP_API}/${userInfo.memberId}/myplants?page=${curPage}&size=3`,
        headers: {
          "Authorization" : jwt
        },
      })
      .then((res) => {
        setTotalElements(res.data.pageInfo.totalElements)
        setTotalPages(res.data.pageInfo.totalPages)
        setMyPlantsData(res.data.data)}
      ).catch((err) => console.error(err))
    } else {
      axios({
        method: "get",
        url: `${process.env.REACT_APP_API}/${userInfo.memberId}/myplants?page=${curPage}&size=4`,
        headers: {
          "Authorization" : jwt
        },
      })
      .then((res) => {
        setTotalElements(res.data.pageInfo.totalElements)
        setTotalPages(res.data.pageInfo.totalPages)
        setMyPlantsData(res.data.data)}
      ).catch((err) => console.error(err))
    }
  };
  
  useEffect(() => getMyPlantsData(), [handleChange, userInfo, curPage, isOwnPage])

  const alertAddPlant = () => {
    alert("더 이상 추가하실 수 없습니다.")
  }

  const handlePlantClick = (el) => {
    setCurrentView("plant")
    setCurrentPlantData(el);
    setIsPanelOpened(true);
  }

  const havePlantDeleted = () => {
    setIsPanelOpened(false);
    setCurrentPlantData(null);
  }

  const handlePrevButtonClick = () => {
    if (myPlantsData.length === 0) {
      return;
    } else if(curPage === 1) {
      return;
    } else {
      setCurPage(curPage - 1);
    }
  };

  const handleNextButtonClick = () => {
    if (myPlantsData.length === 0) {
      return;
    } else if(curPage === totalPages) {
      return;
    } else{
      setCurPage(curPage + 1);
    }
  };

  // const renderPageDots = () => {
  //   const result = [];
  //   for(let i = 0; i < totalPages; i++) {
  //     result.push(<div key={i}></div>)
  //   }
  //   if (result.length === 0) {
  //     return (<div></div>)
  //   }
  //   return result
  // }

  return (
    <StyledContainer>
      <StyledMyPlantsDashBoard>
        <StyledMoveButton
          className={`${totalPages === 0 || curPage === 1 ? "hidden" : ""}`}
          onClick={handlePrevButtonClick}
        >
          <GrPrevious className="icon" />
        </StyledMoveButton>
        <StyledListsContainer>
          {isOwnPage &&           
          <StyledMyPlantsItem onClick={totalElements === 9 ? alertAddPlant : handleAddPlant}>
            <div className="image-wrapper">
              <Cookiee className="image" />
            </div>
            <p>반려식물 추가</p>
          </StyledMyPlantsItem>}
          {myPlantsData.length !== 0 && 
            myPlantsData.map((el) => {
              return (
                <PlantThumbnail 
                  key={el.myPlantsId}
                  handlePlantClick={handlePlantClick}
                  currentPlantData={currentPlantData}
                  el={el}
                  />
              )
              })}
          {myPlantsData.length === 0 && 
            <StyledNoContents>
              <div>
                <RiPlantLine />
              </div>
              <p>등록된 반려식물이 없습니다.</p>
            </StyledNoContents>}
        </StyledListsContainer>
        <StyledMoveButton
          className={`${totalPages === 0 || curPage === totalPages ? "hidden" : ""}` }
          onClick={handleNextButtonClick}
        >
          <GrNext className="icon" />
        </StyledMoveButton>
      </StyledMyPlantsDashBoard>
      {/* <StyledPagination curPage={curPage}>
        {renderPageDots()}
      </StyledPagination> */}
      {isPanelOpened && <MyPlantInfo isOwnPage={isOwnPage} havePlantDeleted={havePlantDeleted} handleChange={handleChange} currentPlantData={currentPlantData} setCurrentPlantData={setCurrentPlantData} currentView={currentView} setCurrentView={setCurrentView}/>}
    </StyledContainer>
  );
};

export default MyPlants;
