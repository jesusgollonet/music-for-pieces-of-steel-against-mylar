#!/bin/bash

function convert_sounds(){
    for i in `ls samples/*.wav`; do 
        echo "$i"; 
        #sox $i $i.wav; 
        #echo -e "$i.wav"; 
    done;
}

convert_sounds
