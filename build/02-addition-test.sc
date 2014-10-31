s.boot
(
"synthdefs.sc".loadRelative;
"utils.sc".loadRelative;
)

(

TempoClock.default.tempo = 360/60;

~player3 = ~player3 ? ();

~player3.count = 0;
~player3.seedPattern =[1,1,1,0,1,1,0,1,0,1,1,0];
~player3.hitPositions = all{:y, y<-(0..(~player3.seedPattern.size)),~player3.seedPattern[y]== 1};
~player3.currentAccumulatedPattern = 0 ! ~player3.seedPattern.size;


Penvir(~player3,
	Pbind(
		\instrument, \pbf,
		\dur, 1,
		\bufnum, Pseq([b.at(\clamp)],inf),
		\pan, Pseq([-0.5,0.5],inf),
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
).play(quant:1);


f = [1,1,1,0,1,1,0,1,0,1,1,0];
g = f.rotate(1);
h = f.rotate(2);

// all patterns 'inherit' from this one
~basePbind = Pbind(
    \instrument, \pbf,
    \dur, 1
);

p = Pbindf(
    ~basePbind,
	\bufnum, Pseq([b.at(\k1r), b.at(\k1p)],inf),
    \pan, 0,
    \amp, Pseq(f,inf) * 0.1
).play(quant:2);


)