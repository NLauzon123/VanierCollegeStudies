class Header {
    constructor() {}
    render() {
        const renderHook = document.getElementById("app");
        const headBand = document.createElement("div");
        headBand.className = "row";
        headBand.id = "headBand";
        headBand.innerHTML = `
            <div class="col-md-2" id="leftHeader" style="background-image: url('assets/images/Nicolas.jpg');"></div>
            <div class="col-md-10">
                <div class="row" id="rightTopHeader">Nicolas' Walk of Life</div>
                <div class="row" id="rightBottomHeader">
                    <span>
                        <button id="button1" onclick=homeContent()>Home</button>
                        <button id="button2" onclick=professionalContent()>Professional</button>
                        <button id="button3" onclick=travelsContent()>Travels</button>
                        <button id="button4" onclick=contactsContent()>Contacts</button>
                        <button id="button5" onclick=creditsContent()>Credits</button>
                    </span>
                </div>
            </div>
        `;
        const content = document.createElement("div");
        content.className = "row";
        content.id = "content";
        content.innerHTML = `
            <div class="row" id="contentTitle">Home page: Let's keep it peaceful.</div>
            <div class="row" id="contentFill">
                <div id="imageFill" style="background-image: url('assets/images/FrontImage.jpg');"></div>
            </div>
        `;
        renderHook.append(headBand);
        renderHook.append(content);
    }
}
const header = new Header();
header.render();
const contentTitle = document.getElementById("contentTitle");
const contentFill = document.getElementById("contentFill");
function cleanContentFill() {
    while(contentFill.firstChild) contentFill.removeChild(contentFill.firstChild);
}
function homeContent() {
    cleanContentFill();
    contentTitle.innerHTML = "Home page: Let's keep it peaceful.";
    var homePage = document.createElement("div");
    homePage.id = "imageFill";
    homePage.style.backgroundImage="url('assets/images/FrontImage.jpg')";
    contentFill.append(homePage);
}
function professionalContent() {
    cleanContentFill();
    contentTitle.innerHTML = "My professional journey so far.";
    var prof = document.createElement("div");
    prof.className = "accordion";
    prof.innerHTML = `
        <div class="accordion-item">
            <div class="accordion-title">Skills</div>
            <div class="accordion-content">
				<h2>Computing</h2>
				<p>Recently trained on C#, JAVA, SQL. Programming experience as part of my engineering practice using Visual Basic and Matlab.
				<h2>Aquired through engineering</h2>
				<p>In addition to the typical application and review of standards of pratice in my career in civil engineering, 
				my activities included project management, staff and student supervision, and oral and written communication on a regular basis.</p>
				<h2>Languages</h2>
				<p>Fluent in French and English (spoken and written).</p>
            </div>
        </div>
        <div class="accordion-item">
            <div class="accordion-title">Education</div>
            <div class="accordion-content">
                <p>2024-now: ACS, Software Development, <a href="https://www.vaniercollege.qc.ca/" target="_blank">Vanier College</a>, Montreal.</p>
				<p>2018-21: LL.B., Faculty of Law, <a href="https://www.umontreal.ca/" target="_blank">Université de Montréal</a>, Montreal.</p>
				<p>1999-03: Ph.D., Civil Engineering, <a href="https://www.ubc.ca/" target="_blank">University of British Columbia</a>, Vancouver.</p>
				<p>1993-95: M.A.Sc., Civil Engineering, <a href="https://polymtl.ca/" target="_blank">École Polytechnique de Montréal</a>, Montreal.</p>
				<p>1989-93: B.Eng., Civil Engineering, <a href="https://polymtl.ca/" target="_blank">École Polytechnique de Montréal</a>, Montreal.</p>
            </div>
        </div>
        <div class="accordion-item">
            <div class="accordion-title">Professional Experience</div>
            <div class="accordion-content">
                <h2>Engineering track</h2>
				<p>From 1993 to 2014, with <a href="https://www.wsp.com/en-ca/" target="_blank">WSP</a> (formerly Golder Associates and Groupe conseil Génivar), 
				<a href="https://www.ulaval.ca/" target="_blank">Université Laval</a>, <a href="https://polymtl.ca/" target="_blank">École Polytechnique de Montréal</a>, 
				<a href="https://www.riotinto.com/en/can/" target="_blank">Rio Tinto</a> (formerly Alcan Aluminum), and <a href="https://www.hydroquebec.com/residentiel/" target="_blank">Hydro-Québec</a>.</p>
				<p>My 20-year commitment in engineering has been dedicated to support mining, oil sands and hydroelectric development projects, from the exploration to 
				the closure and reclamation phases. My work has included water resources management, environmental protection, and hydraulic structure design. 
				Specific tasks have included the application and review of practices in engineering, the development of Excel based applications, involvement in research,
				project management, supervision of employees and students, and oral and written communication to clients and others.</p>
                <h2>Legal track</h2>
				<p>From 2020 to 2022, with the Legal Information Clinic, <a href="https://csu.qc.ca/" target="_blank">Concordia Student Union</a>.</p>
				<p>My tasks at the Legal Information clinic of the Concordia Student Union have entailed conducting interviews and providing legal information to assist
				members of Concordia University’s student population with their legal issues.</p>
            </div>
        </div>
        <div class="accordion-item">
            <div class="accordion-title">Additional Assets</div>
            <div class="accordion-content">
				<p>World-traveled, with an intense exploration period from 2015 to 2016, stepping foot on all inhabited continents, visiting 56 countries, 
				including a few off the track locations (Greenland, Tibet, XinJiang).</p>
				<p>Devoted to photography, yoga, running and high mountain hiking (Everest Base Camp, Inca Trail, Mt Killimanjaro).</p>
            </div>
        </div>
    `;
    contentFill.append(prof);
    var accordionItems = document.querySelectorAll(".accordion-item");
    accordionItems.forEach(item => {
        var title = item.querySelector(".accordion-title");
        var content = item.querySelector(".accordion-content");
        title.addEventListener('click', () => {
            for (var i = 0; i < accordionItems.length; i++) {
                if (accordionItems[i] != item) accordionItems[i].classList.remove('active');
                else item.classList.toggle('active');
            }
        });
    });
}
function travelsContent() {
    cleanContentFill();
    contentTitle.innerHTML = "Travel destinations overview.";
    var tab = document.createElement("div");
    tab.className="tab";
    tab.innerHTML = `
        <button class="tablink" data-tab="tab1">Overview</button>
        <button class="tablink" data-tab="tab2">North-A.</button>
        <button class="tablink" data-tab="tab3">Central-A.</button>
        <button class="tablink" data-tab="tab4">South-A.</button>
        <button class="tablink" data-tab="tab5">Europe</button>
        <button class="tablink" data-tab="tab6">Africa</button>
        <button class="tablink" data-tab="tab7">Asia</button>
        <button class="tablink" data-tab="tab8">oceania</button>
    `;
    contentFill.append(tab);
    var tabH = []; var tabID = []; var tabImage = [];
    tabH[0] = "Where did I go really."; 
    tabH[1] = "North America: Yeah... snow in the summer in the Rockies.";
    tabH[2] = "Central America: So green in the tropics.";
    tabH[3] = "South America: Amazing scenery, everywhere you look.";
    tabH[4] = "Europe: The tranquil elegance of old towns.";
    tabH[5] = "Africa: The wildlife... Gentle, fierce and everything in between.";
    tabH[6] = "Asia: The old, the new, the diversity... Everything, everywhere, all at once.";
    tabH[7] = "Oceania: The diversity of the sceneries so close to each other.";
    tabID[0] = "tab1"; tabID[1] = "tab2"; tabID[2] = "tab3"; tabID[3] = "tab4";
    tabID[4] = "tab5"; tabID[5] = "tab6"; tabID[6] = "tab7"; tabID[7] = "tab8";
    tabImage[0] = "url('assets/images/Voyages.jpg')"; tabImage[1] = "url('assets/images/Canada.jpg')";
    tabImage[2] = "url('assets/images/Guatemala.jpg')"; tabImage[3] = "url('assets/images/Brazil.jpg')";
    tabImage[4] = "url('assets/images/Netherlands.jpg')"; tabImage[5] = "url('assets/images/Botswana.jpg')";
    tabImage[6] = "url('assets/images/Malaysia.jpg')"; tabImage[7] = "url('assets/images/Australia.jpg')";
    for (i = 0; i < 8; i++) {
        var tabParent = document.createElement("div");
        var tabHeader = document.createElement("h2");
        var tabFill = document.createElement("div");
        tabParent.className = "tabcontent";
        tabParent.id = tabID[i];
        tabHeader.innerHTML = tabH[i];
        if (i == 0) tabFill.id = "imageFillOverview";
        else tabFill.id = "imageFill";
        tabFill.style.backgroundImage = tabImage[i];
        tabParent.append(tabHeader);
        tabParent.append(tabFill);
        contentFill.append(tabParent);
    }
    var tabButtons = document.querySelectorAll('.tablink');
    for (var i = 0; i < tabButtons.length; i++) {
        tabButtons[i].addEventListener('click', function() {
            var tabName = this.dataset.tab;
            var tabContent = document.getElementById(tabName);
            var allTabContent = document.querySelectorAll('.tabcontent');
            var allTabButtons = document.querySelectorAll('.tablink');
            for (var j = 0; j < allTabContent.length; j++) allTabContent[j].style.display = 'none';
            for (var j = 0; j < allTabButtons.length; j++) allTabButtons[j].classList.remove('active');
            tabContent.style.display = "block";
            this.classList.add('active');
        });
    }
    document.querySelector('.tablink').click();
}
function contactsContent() {
    cleanContentFill();
    contentTitle.innerHTML = "Where can I be found.";
    var contacts = document.createElement("div");
    contacts.id = "imageFillOverview";
    contacts.innerHTML = `
        <h2>Contact Info</h2>
		<p>Living location: Montreal, QC</p>
		<p>Phone: 514-623-1501</p>
		<p>Email: nicolaslauzon@yahoo.com</p>
		<p>With a digital footprint on <a href="https://ca.linkedin.com/in/nicolaslauzon" target="_blank">LinkedIn</a> and <a href="https://github.com/NLauzon123" target="_blank">GitHub</a></p>
    `;
    contentFill.append(contacts);
}
function creditsContent() {
    cleanContentFill();
    contentTitle.innerHTML = "Yes, all the pictures herein are from me.";
    var credits = document.createElement("div");
    credits.id = "imageFillCredits";
    credits.style.backgroundImage="url('assets/images/NL2016.jpg')";
    contentFill.append(credits);
}