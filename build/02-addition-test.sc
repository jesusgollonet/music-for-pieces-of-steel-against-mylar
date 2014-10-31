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
		\bufnum, Pseq([b.at(\clamp)],inf),
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
		\bufnum, Pseq([b.at(\k1r)],inf),
		\pan, -0.5,
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


f = ~basePattern;

// all patterns 'inherit' from this one
~basePbind = Pbind(
    \instrument, \pbf,
    \dur, 1
);

p = Pbindf(
    ~basePbind,
	\bufnum, Pseq([b.at(\k)],inf),
	\rate, 2,
    \pan, 0,
    \amp, Pseq(f,inf) * 0.1
);


Ptpar([
	0,p,
	12 * 4, ~player3Penvir,
	12 * 4 + 12 * 8, ~player4Penvir,
]).play;

)