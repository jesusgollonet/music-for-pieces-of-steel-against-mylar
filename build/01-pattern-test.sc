

(
SynthDef("pbf", { |out = 0, bufnum = 0, rate = 1, amp = 0.5|
	var player, ratio, panned;
	ratio = BufRateScale.kr(bufnum) * rate;
	player = PlayBuf.ar(1, bufnum, ratio, doneAction:2);
	panned =  Pan2.ar(player , level:amp);
    Out.ar(out, panned);
}).add;
)
(
b = JGBUtil.dirToBuffers("/Users/jesusgollonet/Documents/projects/music-for-pieces-of-steel-against-mylar/dev/build/samples/",s);
)
(

f = [1,1,1,0,1,1,0,1,0,1,1,0];

p = Pbind(
    \instrument, \pbf,
    \buffer, \k1p,
    \dur, 0.15,
    \amp, Pseq(f,inf)
).play;
)