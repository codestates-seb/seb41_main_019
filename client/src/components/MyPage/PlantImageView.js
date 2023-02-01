import styled from "styled-components";
import { useState, useEffect } from "react";
import { IoIosArrowDropleft } from "react-icons/io";
import { IoIosArrowDropright } from "react-icons/io";

const Wrapper = styled.div`
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    width: 1240px;
    height: 900px;
    background-color: white;
    z-index: 1000;

    .none {
        display: none;
    }

    @media screen and (max-width: 1255px) {
        width: 900px;
        height: 900px;
    }

    @media screen and (max-width: 1024px) {
        width: 500px;
        height: 600px;
        top: 38%;
        flex-direction: column;

        svg {
            font-size: 20px;
        }
    }
`;

const StyledView = styled.div`
    ul {
        width: 100%;
        margin: 0px;
        padding: 0px;
        list-style: none;

        li {
            width: 100%;
            height: 100%;
            position: relative;
        }

        li div:first-child {
            height: ${({type}) => type ? "" : "900px"};
            background-color: black;
        }

        li div p {
            width: 100%;
            top: -50px;
            text-align: center;
            position: absolute;
            font-size: 50px;
            font-weight: 100;
            color: white;
            background-color: black;
        }

        li div img {
            object-fit: contain;
            width: 100%;
            height: 100%;
        }

        li div:nth-of-type(2) {
            display: flex;
            justify-content: space-between;
            position: absolute;
            top: 50%;
            width: 100%;
            height: 40px;

            .hidden {
                visibility: hidden;
            }

            svg {
                width: 40px;
                height: 40px;
                fill: #ADADAD;
                margin: 0px 10px 0px 10px;
                border-radius: 30px;
            }
        }
        
        .none {
            display: none;
        }
    }

`

const PlantImageView = ({handlePlantImageView, plantImageViewData}) => {
    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handlePlantImageView();
        })
    },[handlePlantImageView])

    console.log(plantImageViewData)

    const {galleryData, galleryIdx} = plantImageViewData

    const [cur, setCur] = useState(galleryIdx)

    return (
            <Wrapper>
                <StyledView>
                    <ul>
                        { galleryData.map((el, idx) => {
                                return (
                                    <li key={idx} className={cur === idx ? null : "none"}>
                                        <div>
                                            <p>{el.content}</p>
                                            <img src={el.plantImage} alt="img" />
                                        </div>
                                        { galleryData.length > 1 ?
                                            <div className="arrow">
                                                <IoIosArrowDropleft onClick={() => setCur(cur - 1)} className={`pre ${cur === 0 ? "hidden" : null}`} />
                                                <IoIosArrowDropright onClick={() => setCur(cur + 1)} className={`next ${cur === galleryData.length - 1 ? "hidden" : null}`} />
                                            </div> 
                                            : null
                                        }
                                    </li>
                                );
                            })
                        }
                    </ul>
                </StyledView>
            </Wrapper>
    );
}

export default PlantImageView;