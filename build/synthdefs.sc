
SynthDef("pbf", { |out = 0, bufnum = 0, rate = 1, amp = 0.5, pan = 0|
	var player, ratio, panned;
	ratio = BufRateScale.kr(bufnum) * rate;
	player = PlayBuf.ar(1, bufnum, ratio, doneAction:2);
	panned =  Pan2.ar(player , pan,  level:amp);
    Out.ar(0, panned);
}).add;
