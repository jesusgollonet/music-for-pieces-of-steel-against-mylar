s.boot
(
"synthdefs.sc".loadRelative;
"utils.sc".loadRelative;
)

(

~basePattern = [1,1,1,0,1,1,0,1,0,1,1,0];
// ~basePattern = [1,1,0,1,0,1,1,0];
// ~basePattern = [1,1,0,1,1,0];

TempoClock.default.tempo = 400/60;

~player3 = ~player3 ? ();

~player3.count = 0;
~player3.seedPattern =~basePattern;
~player3.hitPositions = all{:y, y<-(0..(~player3.seedPattern.size)),~player3.seedPattern[y]== 1}.scramble;
~player3.currentAccumulatedPattern = 0 ! ~player3.seedPattern.size;


~player3Penvir = Penvir(~player3,
    Pbind(
        \instrument, \pbf,
        \dur, 1,
        \rate, 0.8,
        \bufnum, Pxrand([b.at(\fdv0), b.at(\fdv1),b.at(\fdv2),b.at(\fdv3),b.at(\fdv4),b.at(\fdv5)],inf),
        \pan, 0.5,
        \amp, Pn(Plazy({
            /*
            Every 4 bars, we add a new note to the pattern until we have the 8 of them
            */
            var currentIndex = ~hitPositions[~count];
            ~currentAccumulatedPattern[currentIndex] = ~seedPattern[currentIndex];
            ~currentAccumulatedPattern.postln;
            ~count = ~count + 1;
            ~count = ~count % ~hitPositions.size;
            Pn(Pseq(~currentAccumulatedPattern,1),4);
        }) * 0.1,inf);
    )
);


~player4 = ~player4 ? ();

~player4.count = 0;
~player4.seedPattern =~basePattern;
~player4.hitPositions = all{:y, y<-(0..(~player3.seedPattern.size)),~player3.seedPattern[y]== 1}.scramble;
~player4.currentAccumulatedPattern = 0 ! ~player3.seedPattern.size;


~player4Penvir = Penvir(~player4,
    Pbind(
        \instrument, \pbf,
        \dur, 1,
        \bufnum, Pxrand([b.at(\fastson0),b.at(\fastson1),b.at(\fastson2),b.at(\fastson3),b.at(\fastson4)],inf),
        \pan, -0.5,
        \rate, 0.8,
        \amp, Pn(Plazy({
            /*
            Every 4 bars, we add a new note to the pattern until we have the 8 of them
            */
            var currentIndex = ~hitPositions[~count];
            ~currentAccumulatedPattern[currentIndex] = ~seedPattern[currentIndex];
            ~currentAccumulatedPattern.postln;
            ~count = ~count + 1;
            ~count = ~count % ~hitPositions.size;
            Pn(Pseq(~currentAccumulatedPattern,1),4);
        }) * 0.25,inf);
    )
);



f = ~basePattern;

// all patterns 'inherit' from this one
~basePbind = Pbind(
    \instrument, \pbf,
    \dur, 1
);

p = Pbindf(
    ~basePbind,
    \bufnum, Pxrand([b.at(\facemusic0),b.at(\facemusic1), b.at(\facemusic2), b.at(\facemusic3)],inf),
    \rate, 1,
    \pan, 0,
    \amp, Pseq(f,inf) * 0.1
);


Ptpar([
    0,p,
    12 * 4 + 1, ~player3Penvir,
    12 * 4 + 12 * 8 + 2, ~player4Penvir,
]).play;

)
