<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pictby.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pictby.utils.*" %>
<%@ page import="java.util.Map" %>
<%
List<Item> newItemList =(List<Item>) request.getAttribute("newItemList");
List<User> newUserList =(List<User>) request.getAttribute("newUserList");
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
<style>

</style>
</head>
	
<body>

	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->

		<!-- Slider start -->
	<section id="home" class="no-padding">	
    	<div id="main-slide" class="cd-hero">
			<ul class="cd-hero-slider">
				<li class="selected">
					<div class="overlay2">
						<img class="" src="/images/ciel.jpg" alt="slider">
					</div>
					<div class="cd-full-width">
						<h2>才能あふれるクリエイターの画像素材を無料で使いましょう。<br />FREE TO USE THE CREATOR'S PICTURES</h2>
							
						<h3>登録不要。商用非商用問わず無料。高品質なクリエイターの画像素材をかんたんにあなたのサイトに埋め込むことができます。</h3>
						<div style="padding-bottom:20px;">
							<div class="search-box text-center">
								<form id="search" action="/kws" method="get">
									<div class="input-group">
                    					<span class="input-group-addon"><i class="fa fa-search"></i></span>
                    					<input type="text" class="form-control" placeholder="Keywords" name="keyword">
                   	 					<input type="submit" value="検索" style="display: none;">
               		   				</div>
           		       			</form>
           		       		</div>
                 	 	</div>
                 	 	<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								class="btn btn-primary white cd-btn"
								href="/preparing.html">
                 	 	<%=App.APP_DISPLAY_NAME %> とは</a>
						<a href="#creator-register" class="cd-btn btn secondary btn-primary solid page-scroll">クリエイターの方はこちらへ</a>
					</div> <!-- .cd-full-width -->
				</li>
			</ul> <!--/ cd-hero-slider -->

		</div><!--/ Main slider end -->    	
    </section> <!--/ Slider end -->

   
	<!-- Portfolio start -->
	<section id="portfolio" class="portfolio">
		<div class="container">
			<div class="row">
				<div class="col-md-12 heading">
					<span class="title-icon classic pull-left"><i class="fa fa-camera"></i></span>
					<h2 class="title classic">New Portfolio</h2>
				</div>
			</div> <!-- Title row end -->
		</div>
		
		<div class="container-fluid">
			<div class="row">
				<div id="isotope" class="isotope">
					<%for(Item item: newItemList) { %>
					<div class="col-md-3 col-xs-12 item-box wow fadeInUp animated">
						<figure class="" data-wow-duration="500ms" data-wow-delay="0ms">
							<div class="img-wrapper" style="background-image: url(<%=item.getOriginalImageUrl() %>)">
								<a href="/<%=item.getUserId() %>/item/<%=item.getKey().getName() %>"></a>
							</div>
						</figure>
					</div><!--/ item end -->
					<%} %>
					
				</div><!-- Isotope content end -->
			</div><!-- Content row end -->
		</div><!-- Container end -->
	</section><!-- Portfolio end -->
	
	<section id="team" class="team">
		<div class="container">
			<div class="row">
				<div class="col-md-12 heading">
					<span class="title-icon classic pull-left"><i class="fa fa-user"></i></span>
					<h2 class="title classic">New Creator</h2>
				</div>
			</div> <!-- Title row end -->
		</div>
		
		<div class="container">
			<div class="row text-center">
				<%for(User user: newUserList) { 
					Map<String,SocialLink> socialLinkMap = user.getSocialLinkMap();
				%>
				<div class="col-md-3 col-sm-6">
					<div class="team wow slideInLeft animated" style="visibility: visible;">
						<div class="img-hexagon">
							<a href="/<%=user.getUserId() %>">
								<img src="<%=user.getIconImageResources() != null ? user.getIconImageUrl() + "=s150" : "/user/pub/images/icon-user-default.png" %>">
							</a>
							<span class="img-top"></span>
							<span class="img-bottom"></span>
						</div>
						<div class="team-content">
							<h3 class="text-ellipsis"><a href="/<%=user.getUserId() %>"><%=user.getName() %></a></h3>
							<p class="text-ellipsis"><%=user.getCatchCopy() %></p>
							<div class="team-social">
								<%for(Map.Entry<String,SocialLink> linkEntry: socialLinkMap.entrySet()) {
										SocialLink socialLink = linkEntry.getValue();
										Social social = socialLink.getSocial();
										String baseUrl = StringUtil.isEmpty(social.getBaseUrl()) ? "" : social.getBaseUrl();
								%>
								<a class="<%=social.getLinkStyleClass() %>" href="<%=baseUrl + socialLink.getUrlPath() %>"><i class="<%=social.getIconStyleClass() %>"></i></a>
								<%} %>
							</div>
						</div>
					</div>	
				</div><!--/ Team 1 end -->
				<%} %>
				
			</div><!--/ Content row end -->
		</div><!--/ Container end -->
    </section>
	
	<section id="creator-register" class="parallax parallax5">
		<div class="parallax-overlay"></div>
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h2>あなたの才能を作品とともに世界に知らしめましょう。</h2>
					<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay=".5s" style="visibility: visible;-webkit-animation-delay: .5s; -moz-animation-delay: .5s; animation-delay: .5s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/step1.png" alt=""></span>
						<h3>ポートフォリオをかんたんに作成</h3>
						<p>PICTBYにクリエイター登録してあなたの作品をアップロード。ポートフォリオをかんたんに作成して公開することができます。</p>
					</div>
				</div><!--/ End first service -->

				<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay=".8s" style="visibility: visible;-webkit-animation-delay: .8s; -moz-animation-delay: .8s; animation-delay: .8s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/step2.png" alt=""></span>
						<h3>世界中のサイトがあなたの作品を掲載</h3>
						<p>ポートフォリオに登録された作品はPICTBYの検索対象となり世界中のブログやメディアに埋め込みタグを通じて掲載されます。</p>
					</div>
				</div><!--/ End Second features -->

				<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay="1.1s" style="visibility: visible;-webkit-animation-delay: 1.1s; -moz-animation-delay: 1.1s; animation-delay: 1.1s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/step3.png" alt=""></span>
						<h3>クレジット表記とリンクでチャンスが広がる</h3>
						<p>各サイトに埋め込まれた作品には自動であなたのクレジットが表示されます。さらに画像全体があなたのポートフォリオへのリンクとなっているのでチャンスが広がります。</p>
					</div>
				</div><!--/ End Third features -->
					
				</div>
			</div>
			<div class="row" style="margin-top: 30px;">
				<div class="col-md-12 text-center">
					<p>
						<a href="/user/account/register" class="btn btn-primary white">今すぐクリエイター登録</a>
						<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								class="cd-btn btn secondary btn-primary solid" 
								style="background-color: rgba(22, 26, 30, 0.8);"
								href="/preparing.html">
						もっと詳しく</a>
					</p>
				</div>
			</div>
		</div><!-- Container end -->
    </section>
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
</body>
</html>
