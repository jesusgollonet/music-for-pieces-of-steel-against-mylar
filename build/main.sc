s.boot

(
"synthdefs.sc".loadRelative;
"utils.sc".loadRelative;
)

(

var sounds1, sounds2, sounds3,
    longPattern, midPattern, shortPattern, currentPattern,
    createPlayer;

sounds1 = [b.at(\facemusic0),b.at(\facemusic1), b.at(\facemusic2), b.at(\facemusic3)];
sounds2 = [b.at(\fdv0), b.at(\fdv1),b.at(\fdv2),b.at(\fdv3),b.at(\fdv4),b.at(\fdv5)];
sounds3 = [b.at(\fastson0),b.at(\fastson1),b.at(\fastson2),b.at(\fastson3),b.at(\fastson4)];

longPattern = [1,1,1,0,1,1,0,1,0,1,1,0];
midPattern = [1,1,0,1,0,1,1,0];
shortPattern = [1,1,0,1,1,0];
currentPattern = longPattern;


TempoClock.default.tempo = 400/60;

// ------------------------------------------------------------
// parameters
// hit positions
// offset
// sounds

createPlayer = {|seedPattern, offset, soundsArr, amp, pan|
    var player,playerPattern;
    player = player ? ();
    player.playCount = 0;
    player.seedPattern = seedPattern;
    player.hitPositions = all{:y, y<-(0..(player.seedPattern.size)),player.seedPattern[y]== 1}.scramble;
    player.currentAccumulatedPattern = 0 ! player.seedPattern.size;

    playerPattern = Penvir(player,
        Pbind(
        \instrument, \pbf,
        \dur, 1,
        \rate, 0.8,
        \bufnum, Pxrand(soundsArr,inf),
        \pan, pan,
        \amp, Pn(Plazy({
            /*
            Every 4 bars, we add a new note to the pattern until we have the 8 of them
            */
            var currentIndex = ~hitPositions[~playCount];
            ~currentAccumulatedPattern[currentIndex] = ~seedPattern[currentIndex];
            ~currentAccumulatedPattern.postln;
            ~playCount = ~playCount + 1;
            ~playCount = ~playCount % ~hitPositions.size;
            Pn(Pseq(~currentAccumulatedPattern,1),4);
            }) * amp,inf);
        )
    );
};

// ------------------------------------------------------------
f = currentPattern;

// all patterns 'inherit' from this one
~basePbind = Pbind(
    \instrument, \pbf,
    \dur, 1
);

p = Pbindf(
    ~basePbind,
    \bufnum, Pxrand(sounds1,inf),
    \rate, 1,
    \pan, 0,
    \amp, Pseq(f,inf) * 0.1
);




Ptpar([
    0,p,
    12 * 4 + 1, createPlayer.(currentPattern, 0, sounds2, 0.1, -0.5),
    12 * 4 + 12 * 8 + 2, createPlayer.(currentPattern, 0, sounds3, 0.4, 0.5),
]).play;

)

